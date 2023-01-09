import streamlit as st

from database_handling import Database


def show_wish_list_page():
    db = Database()
    df = db.get_data_from_WL()
    st.write("### Here is your wish list")
    # remove several not much useful cols
    # from your df before showing it
    df_shortened = df.drop(columns=["description",
                                    "language",
                                    "page_URL",
                                    "cover_image_URL"])
    st.dataframe(df_shortened)
