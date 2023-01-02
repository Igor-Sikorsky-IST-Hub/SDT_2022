import streamlit as st

from database_handling import establish_connection
from database_handling import get_every_book_from_DB


def show_all_books_page():
    engine = establish_connection()
    df = get_every_book_from_DB(engine)
    st.write("### Here are all your scraped books")
    # remove several not much useful cols
    # from your df before showing it
    df_shortened = df.drop(columns=["description",
                                    "language",
                                    "page_URL",
                                    "cover_image_URL"])
    st.dataframe(df_shortened)

    # Maybe add the possibility to add books to wish list here
    pass
