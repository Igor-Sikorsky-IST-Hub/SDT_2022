import streamlit as st


from scrape_page import show_scrape_page
from all_books_page import show_all_books_page
from wish_list_page import show_wish_list_page
from detailed_book_info_page import show_detailed_book_info_page


def main():
    st.title("Audible Web Scraper")

    page = st.sidebar.selectbox("Choose page",
                                ("Scrape webpage",
                                 "See all books",
                                 "See wish list",
                                 "See detailed book info"))
    if page == "Scrape webpage":
        show_scrape_page()
    elif page == "See all books":
        show_all_books_page()
    elif page == "See wish list":
        show_wish_list_page()
    elif page == "See detailed book info":
        show_detailed_book_info_page()


if __name__ == "__main__":
    main()
