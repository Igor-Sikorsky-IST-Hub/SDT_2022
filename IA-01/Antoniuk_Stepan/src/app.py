import streamlit as st

from scrape_page import show_scrape_page
from all_books_page import show_all_books_page

st.title("Audible Web Scraper")

page = st.sidebar.selectbox("Choose page", ("Scrape", "See all books", "See wish list"))
if page == "Scrape":
    show_scrape_page()
elif page == "See all books":
    show_all_books_page()
