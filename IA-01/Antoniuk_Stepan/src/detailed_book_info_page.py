import streamlit as st


from database_handling import establish_connection
from database_handling import get_every_book_from_DB


def show_detailed_book_info_page():
    st.write("### Here you can see more info about the book you chose")
    st.write("To see the info, we need your book's ID")

    engine = establish_connection()
    df = get_every_book_from_DB(engine)

    book_id = st.text_input(label="Insert book's ID",
                            label_visibility="hidden",
                            placeholder="Insert book ID")
    if book_id.isdigit():
        book_id = int(book_id)
        if book_id < 1:
            st.write("The book with this ID isn't present in DB")
            st.write("The book's IDs start with 1")
        else:
            try:
                # need to subract 1 because it shows the book with
                # incorrect by +1 ID without it
                book_id -= 1
                book_info = df.iloc[book_id]
                st.write(book_info)
            except IndexError:
                st.write("The book with this ID isn't present in DB")
