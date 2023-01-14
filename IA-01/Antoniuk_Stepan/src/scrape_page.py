import streamlit as st

from scraping import AudibleScraper
from database_handling import Database


# is it possible to do this '@' operation in scraping.py?
@st.cache(suppress_st_warning=True)
def get_df(URL):
    scraper = AudibleScraper(URL)
    return scraper.perform_scraping()


def show_scrape_page():
    st.write("### Scrape webpage")
    URL = st.text_input("Paste URL to scrape here:",
                        placeholder="You haven't pasted any URL yet")
    if len(URL) > 1:
        st.write("URL len equals to ", len(URL))
        df = get_df(URL)
        st.write("#### Resulting df")
        st.dataframe(df)

        # Button to Choose whether you want to add this df to DB
        every_df_book_to_DB = st.button("Add every book to DB")
        if every_df_book_to_DB:
            # Add books to DB by already created func
            # After that, show your full df on a new page
            db = Database()
            db.append_to_DB(df)
            st.write("Appended successfully")
