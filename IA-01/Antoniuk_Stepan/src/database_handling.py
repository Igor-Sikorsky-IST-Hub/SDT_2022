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


def load_to_DB(engine, input_df):
    try:
        all_info_from_db = pd.read_sql("select * from books;",
                                       con=engine, index_col="id")
        # since 'rating count' is easily susceptible to change,
        # put this col into a var and append it to the
        # resulting df afterwards
        input_rating_count = input_df["rating_count"]
        # If you load to db straightaway, you can get dublicate values
        # To solve the problem, perform left outer join to get rows
        # belonging only to input df based on
        # https://stackoverflow.com/a/50543455/11749578
        # Before merge, drop col 'rating_count' because this col
        # contains values that often change after each scraping
        # and thus drop_duplicates func thinks it's an unencountered
        # before book when in reality it's not
        left_outer_df = pd.merge(input_df.drop(columns=["rating_count"]),
                                 all_info_from_db.drop(
                                     columns=["rating_count"]),
                                 how="outer", indicator=True
                                 ).query('_merge=="left_only"')
        left_outer_df = left_outer_df.drop(columns=["_merge"])
        subset = ["title", "author", "reg_price", "audio_len", "language"]
        left_outer_df.drop_duplicates(subset=subset, keep="first",
                                      inplace=True)
        # put the 'rating_count' col back to its place
        left_outer_df.insert(4, "rating_count", input_rating_count)
        left_outer_df.reset_index(drop=True, inplace=True)
        # load all info to DB
        left_outer_df.to_sql(name="books", if_exists='append',
                             con=engine, index=False)
    except ProgrammingError:
        print("The table 'books' doesn't exist")
        print("Creating one and adding info to it right now...")
        create_books_table(engine)
        print("Table created")
        input_df.to_sql(name="books", if_exists='append',
                        con=engine, index=False)


def get_every_book_from_DB(engine):
    return pd.read_sql("select * from books;", con=engine, index_col="id")
