import streamlit as st

from scrape_page import show_scrape_page
from all_books_page import show_all_books_page
from detailed_book_info_page import show_detailed_book_info_page

st.title("Audible Web Scraper")

page = st.sidebar.selectbox("Choose page",
                            ("Scrape", "See all books",
                             "See wish list",
                             "See detailed book info"))
if page == "Scrape":
    show_scrape_page()
elif page == "See all books":
    show_all_books_page()
elif page == "See wish list":
    pass
elif page == "See detailed book info":
    show_detailed_book_info_page()
