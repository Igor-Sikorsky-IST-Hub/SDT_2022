import pandas as pd
from sqlalchemy import create_engine
from sqlalchemy import MetaData
from sqlalchemy import Table, Column, Integer, String
from sqlalchemy.exc import ProgrammingError

from env_vars import DB_USER, DB_PASSWORD


class Database:
    def __init__(self):
        self.engine = self.establish_connection()

    def establish_connection(self):
        DB_TO_WORK_WITH = "audible_books_db"
        DB_HOST = "localhost:3306"
        return create_engine(
            f"mysql+pymysql://{DB_USER}:{DB_PASSWORD}@{DB_HOST}/{DB_TO_WORK_WITH}",
            pool_recycle=3600)

    def create_books_table(self):
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
            Column("language", String(31)),
            Column("page_URL", String(1023)),
            Column("cover_image_URL", String(1023))
        )
        metadata_obj.create_all(self.engine)

    def append_to_DB(self, input_df):
        try:
            all_info_from_db = pd.read_sql("select * from books;",
                                           con=self.engine, index_col="id")
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
            )
            subset = ["title", "author", "reg_price", "audio_len", "language"]
            left_outer_df.drop_duplicates(subset=subset, keep=False,
                                          inplace=True)

            left_outer_df = left_outer_df.query('_merge=="left_only"')
            left_outer_df = left_outer_df.drop(columns=["_merge"])
            # put the 'rating_count' col back to its place
            left_outer_df.insert(4, "rating_count", input_rating_count)
            left_outer_df.reset_index(drop=True, inplace=True)
            # load all info to DB
            left_outer_df.to_sql(name="books", if_exists='append',
                                 con=self.engine, index=False)
        except ProgrammingError:
            print("The table 'books' doesn't exist")
            print("Creating one and adding info to it right now...")
            self.create_books_table()
            print("Table created")
            input_df.to_sql(name="books", if_exists='append',
                            con=self.engine, index=False)

    # POSTPHONED TILL BETTER TIME
    def create_wish_list_table(self):
        # metadata_obj = MetaData()
        # wish_list = Table(
        #     "wish_lists",
        #     metadata_obj,
        #     Column("id", Integer, primary_key=True, autoincrement=True),
        #     Column("book_id", Integer, ForeignKey("books.id"), unique=True),
        # )
        # metadata_obj.create_all(self.engine)
        pass

    def get_data_from_DB(self):
        return pd.read_sql("select * from books;", con=self.engine, index_col="id")

    def get_data_from_WL(self):
        sql_string = ("SELECT books.id, books.title, books.description,"
                      "books.author, books.rating, books.rating_count,"
                      "books.reg_price, books.audio_len, books.language,"
                      "books.page_URL, books.cover_image_URL FROM wish_lists"
                      "\ninner join books on wish_lists.book_id = books.id;")
        return pd.read_sql(sql_string, con=self.engine, index_col="id")
