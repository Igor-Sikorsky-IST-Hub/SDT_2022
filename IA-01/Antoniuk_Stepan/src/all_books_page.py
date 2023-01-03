import streamlit as st
import pandas as pd
from sqlalchemy.exc import IntegrityError

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
        add_selected_books_to_wish_list = st.button(
            "Add selected books to wish list")
        if add_selected_books_to_wish_list:
            engine = establish_connection()
            try:
                indexes.to_sql(name="wish_lists", if_exists='append',
                               con=engine, index=False)
                st.write("Added successfully")
            except IntegrityError:
                st.write("You're trying to add dublicates")
                st.write("Trying to remove them...")
                WL_ids = pd.read_sql(
                    "select * from wish_lists;", con=engine, index_col="id")
                WL_ids_list = WL_ids["book_id"].to_list()
                input_ids_list = indexes["book_id"].to_list()
                cleaned = [
                    id for id in input_ids_list if id not in WL_ids_list]
                st.write("Removal completed")
                if len(cleaned) == 0:
                    part_1 = "After dublicate removal"
                    part_2 = "there's no ID to add"
                    st.write(f"{part_1}, {part_2}")
                else:
                    st.write("Adding not present yet id(s) to wish list...")
                    indexes = pd.DataFrame({"book_id": cleaned})
                    indexes.to_sql(name="wish_lists", if_exists='append',
                                   con=engine, index=False)
                    st.write("Operation finished successfully")
