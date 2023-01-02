import streamlit as st


from database_handling import establish_connection
from database_handling import get_every_book_from_DB


def show_detailed_book_info_page():
    st.write("### Here you can see more info about the book you chose")
    st.write("To see the info, we need your chosen book's ID")

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
                chosen_book_info = df.iloc[book_id]

                # unpack variables
                title, description, author,  = chosen_book_info[:3]
                rating, rating_count, reg_price = chosen_book_info[3:6]
                audio_len, language, page_url = chosen_book_info[6:9]
                image_url = chosen_book_info[-1]

                # display book info
                st.write("")
                st.write("")
                st.write("")
                st.image(image_url)
                st.write(f"###### Title: {title}")
                st.write(f"###### Description: {description}")
                st.write(f"###### Author: {author}")
                st.write(f"###### Rating: {rating}")
                st.write(f"###### Rating count: {rating_count}")
                st.write(f"###### Regular price: {reg_price}")
                st.write(f"###### Audio length: {audio_len}")
                st.write(f"###### Language: {language}")
                st.write("")
                st.write(
                    f"##### For more info, see [book's page on audible]({page_url})")
            except IndexError:
                st.write("The book with this ID isn't present in DB")
