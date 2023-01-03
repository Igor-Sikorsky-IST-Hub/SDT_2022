import streamlit as st

from database_handling import establish_connection
from database_handling import get_every_book_from_wish_list


def show_wish_list_page():
    engine = establish_connection()
    df = get_every_book_from_wish_list(engine)
    st.write("### Here is your wish list")
    # remove several not much useful cols
    # from your df before showing it
    df_shortened = df.drop(columns=["description",
                                    "language",
                                    "page_URL",
                                    "cover_image_URL"])
    st.dataframe(df_shortened)
