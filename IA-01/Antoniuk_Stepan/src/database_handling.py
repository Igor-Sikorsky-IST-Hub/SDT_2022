import pandas as pd
from sqlalchemy import create_engine
from sqlalchemy import MetaData
from sqlalchemy import Table, Column, Integer, String
from sqlalchemy.exc import ProgrammingError

from env_vars import DB_USER, DB_PASSWORD


def establish_connection():
    DB_TO_WORK_WITH = "audible_books_db"
    DB_HOST = "localhost:3306"
    return create_engine(
        f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}/{DB_TO_WORK_WITH}",
        pool_recycle=3600)


# Check whether it's working
def create_books_table(engine):
    metadata_obj = MetaData()
    books = Table(
        "books",
        metadata_obj,
        Column("id", Integer, primary_key=True, autoincrement=True),
        Column("title", String(255), nullable=False),
        Column("description", String(255)),
        Column("author", String(255), nullable=False),
        Column("rating", String(31)),
        Column("rating_count", String(15)),
        Column("reg_price", String(15)),
        Column("audio_len", String(31)),
        Column("language", String(31))
    )
    metadata_obj.create_all(engine)


# TRUNCATE DOESN'T WORK WITH FOREING KEYS. REQUIRES REWORKING
# If you load to db straightaway, you can get dublicate values
# To solve this problem, read everything from your DB table into df,
# combine with fresh data, remove dublicates,
# truncate your DB table, and load info back to DB
# Warning: may be slow with big amount of data
def load_to_DB(engine, input_df):
    try:
        all_info_from_db = pd.read_sql("select * from books;", con=engine, index_col="id")
        # combine two dfs
        df_combined = pd.concat([all_info_from_db, input_df])
        # remove dublicates and reset index
        df_combined.drop_duplicates(subset=["title", "author", "reg_price", "audio_len", "language"],
                                    ignore_index=True, inplace=True)
        # Truncate table 'books'
        engine.connect().execute("TRUNCATE TABLE books")
        # load all info to DB
        df_combined.to_sql(name="books", if_exists='append', con=engine, index=False)
    except ProgrammingError:
        print("The table doesn't exist. Creating one and adding info to it right now...")
        create_books_table()
        input_df.to_sql(name="books", if_exists='append', con=engine, index=False)


def get_every_book_from_DB(engine):
    return pd.read_sql("select * from books;", con=engine, index_col="id")