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
        # URLs for testing purposes
        # URL = "https://www.audible.com/search?crid=31E3J6JGY980O&i=na-audible-us&k=brandon+sanderson&keywords=brandon+sanderson&ref-override=a_search_t1_header_search&sort=pubdate-desc-rank&sprefix=brand%2Cna-audible-us%2C170&url=search-alias%3Dna-audible-us&ref=a_search_c1_sort_1&pf_rd_p=073d8370-97e5-4b7b-be04-aa06cf22d7dd&pf_rd_r=VRAHHY22TC5WB0JFHJFR"
        # URL = "https://www.audible.com/adblbestsellers?searchCategory=18580540011&ref=a_adblbests_l1_catRefs_20&pf_rd_p=2ea8d46b-3372-49db-8ad4-77416e49695f&pf_rd_r=1DF75X5DWF19XQYMVZ07"
        # first URL is link to best selers fantasy books
        # URL = "https://www.audible.com/adblbestsellers?searchCategory=18580606011&ref=a_adblbests_l1_catRefs_21&pf_rd_p=2ea8d46b-3372-49db-8ad4-77416e49695f&pf_rd_r=KMSV1YK1NFAHZ0ZJ1X0Q"
        # second URL is link to JRR Roling author page
        # URL = "https://www.audible.com/author/JK-Rowling/B000AP9A6K?ref=a_adblbests_c3_lAuthor_1_15_1&pf_rd_p=4100380b-3e9d-4594-990a-9c93d1a8dac3&pf_rd_r=K616B6NTP46T1CFVKYEZ&pageLoadId=RXtEBMfs0bqiSlGb&creativeId=c0fa3e0e-26f3-4d1a-8e3f-5d3cf9e768e9"

        df = get_df(URL)
        st.write("#### Resulting df")
        st.dataframe(df)

        # Button to Choose whether you want to add this df to DB
        every_df_book_to_DB = st.button("Add every book to DB")
        if every_df_book_to_DB:
            # Add books to DB by already created func
            # After that, show your full df on a new page
            db = Database()
            df = db.get_data_from_DB()
            st.write("Appended successfully")
