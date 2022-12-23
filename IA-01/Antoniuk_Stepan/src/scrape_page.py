import streamlit as st

from scraping import audible_scraper


def show_page():
    # Add sidebar somewhere around here
    st.title("Audible Web Scraper")
 
    URL = st.text_input("Paste URL to scrape here:",
                        placeholder="You haven't pasted any URL yet")

    st.write("URL len equals to ", len(URL))
    # URL = "https://www.audible.com/search?crid=31E3J6JGY980O&i=na-audible-us&k=brandon+sanderson&keywords=brandon+sanderson&ref-override=a_search_t1_header_search&sort=pubdate-desc-rank&sprefix=brand%2Cna-audible-us%2C170&url=search-alias%3Dna-audible-us&ref=a_search_c1_sort_1&pf_rd_p=073d8370-97e5-4b7b-be04-aa06cf22d7dd&pf_rd_r=VRAHHY22TC5WB0JFHJFR"
    df = audible_scraper(URL)
    st.write("#### Resulting df")
    st.dataframe(df)

    # Unfortunately, streamlit doesn't let you select rows and add
    # them to another df. So there exists a workaround of this issue:
    # https://gist.github.com/treuille/e8f07ebcd92265a68ecec585f7594918
    selected_indices = st.multiselect('Select rows:', df.index)
    selected_rows = df.loc[selected_indices]
    st.write('#### Selected Rows', selected_rows)

    # Button to Choose whether you want to add this df to DB
    pass
    # Button to select rows (books) for wish list
    pass


show_page()
