import streamlit as st

from database_handling import establish_connection
from database_handling import get_every_book_from_DB


def show_all_books_page():
    engine = establish_connection()
    df = get_every_book_from_DB(engine)
    st.write("#### Here are all your scraped books")
    st.dataframe(df)

    # Maybe add the possibility to add books to wish list here
    pass
