import streamlit as st

from scraping import audible_scraper


def show_page():
    st.title("Audible Web Scraper")
    URL = st.text_input("Paste URL to scrape here:",
                        placeholder="You haven't pasted any URL yet")
    if len(URL) > 0:
        st.write("URL len equals to ", len(URL))
        URL = "https://www.audible.com/search?crid=31E3J6JGY980O&i=na-audible-us&k=brandon+sanderson&keywords=brandon+sanderson&ref-override=a_search_t1_header_search&sort=pubdate-desc-rank&sprefix=brand%2Cna-audible-us%2C170&url=search-alias%3Dna-audible-us&ref=a_search_c1_sort_1&pf_rd_p=073d8370-97e5-4b7b-be04-aa06cf22d7dd&pf_rd_r=VRAHHY22TC5WB0JFHJFR"
        df = audible_scraper(URL)
        st.dataframe(df)


show_page()
