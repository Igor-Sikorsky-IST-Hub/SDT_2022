import streamlit as st
import pandas as pd

from database_handling import establish_connection
from database_handling import get_every_book_from_DB
from database_handling import add_books_to_wish_list


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

    # A little identation before adding to WL functionality
    st.write("")
    st.write("")
    # Unfortunately, streamlit df doesn't let you select rows and add
    # them to another df. However, there exists a workaround:
    # https://gist.github.com/treuille/e8f07ebcd92265a68ecec585f7594918
    selected_indices = st.multiselect("Select books' indexes", df.index)
    selected_rows = df.loc[selected_indices]
    if len(selected_rows) > 0:
        st.write('#### Selected Books', selected_rows)
        id_list = selected_rows.index.values.tolist()
        indexes = pd.DataFrame({"book_id": id_list})
        # Button to choose whether you want to add this df to wish list
        every_df_book_to_DB = st.button("Add selected books to wish list")
        if every_df_book_to_DB:
            # Add books to DB by already created func
            # After that, show your full df on a new page
            engine = establish_connection()
            add_books_to_wish_list(engine, indexes)
            st.write("Added successfully")
