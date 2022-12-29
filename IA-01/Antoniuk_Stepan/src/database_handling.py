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


# If you load to db straightaway, you can get dublicate values
# To solve the problem, perform left outer join to get rows
# belonging only to input df. Then drop_dublicates
def load_to_DB(engine, input_df):
    try:
        all_info_from_db = pd.read_sql("select * from books;",
                                       con=engine, index_col="id")
        # from https://stackoverflow.com/a/50543455/11749578
        # Perform left outer join to get rows belonging only
        # to input df. Then drop_dublicates
        left_outer_df = pd.merge(input_df, all_info_from_db, how="outer",
                                 indicator=True
                                 ).query('_merge=="left_only"')
        left_outer_df = left_outer_df.reset_index(
            drop=True).drop(columns=["_merge"])
        subset = ["title", "author", "reg_price", "audio_len", "language"]
        left_outer_df.drop_duplicates(subset=subset, keep="first",
                                      ignore_index=True, inplace=True)
        # load all info to DB
        left_outer_df.to_sql(name="books", if_exists='append',
                             con=engine, index=False)
    except ProgrammingError:
        print("The table 'books' doesn't exist")
        print("Creating one and adding info to it right now...")
        create_books_table(engine)
        input_df.to_sql(name="books", if_exists='append',
                        con=engine, index=False)


def get_every_book_from_DB(engine):
    return pd.read_sql("select * from books;", con=engine, index_col="id")
