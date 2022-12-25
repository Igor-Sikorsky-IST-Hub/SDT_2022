import streamlit as st

from scraping import audible_scraper


# is it possible to do this '@' operation in scraping.py?
@st.cache(suppress_st_warning=True)
def get_df(URL):
    return audible_scraper(URL)


def show_page():
    # Add sidebar somewhere around here
    st.title("Audible Web Scraper")
    URL = st.text_input("Paste URL to scrape here:",
                        placeholder="You haven't pasted any URL yet")
    if len(URL) > 1:
        st.write("URL len equals to ", len(URL))
        URL = "https://www.audible.com/search?crid=31E3J6JGY980O&i=na-audible-us&k=brandon+sanderson&keywords=brandon+sanderson&ref-override=a_search_t1_header_search&sort=pubdate-desc-rank&sprefix=brand%2Cna-audible-us%2C170&url=search-alias%3Dna-audible-us&ref=a_search_c1_sort_1&pf_rd_p=073d8370-97e5-4b7b-be04-aa06cf22d7dd&pf_rd_r=VRAHHY22TC5WB0JFHJFR"
        # URL = "https://www.audible.com/adblbestsellers?searchCategory=18580606011&ref=a_adblbests_l1_catRefs_21&pf_rd_p=2ea8d46b-3372-49db-8ad4-77416e49695f&pf_rd_r=KMSV1YK1NFAHZ0ZJ1X0Q"
        df = get_df(URL)
        st.write("#### Resulting df")
        st.dataframe(df)

        # Button to select rows (books) for wish list
        select_books_for_wish_list = st.button("Select books for your wish list")
        if select_books_for_wish_list:
            # Unfortunately, streamlit df doesn't let you select rows and add
            # them to another df. However, there exists a workaround of this issue:
            # https://gist.github.com/treuille/e8f07ebcd92265a68ecec585f7594918
            selected_indices = st.multiselect('Select rows:', df.index)
            selected_rows = df.loc[selected_indices]
            st.write('#### Selected Rows', selected_rows)
            # Maybe button to append rows to wish list table?
            pass

        # Button to Choose whether you want to add this df to DB
        every_df_book_to_DB = st.button("Add every book to DB")
        if every_df_book_to_DB:
            # Add books to DB by already created func
            # After that, show your full df on a new page
            pass


show_page()
