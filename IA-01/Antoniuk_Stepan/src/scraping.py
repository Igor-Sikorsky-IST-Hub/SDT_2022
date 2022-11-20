from bs4 import BeautifulSoup
import requests
import pandas as pd


from book_info import get_book_names
from book_info import get_description
from book_info import get_author
from book_info import get_rating
from book_info import get_nunber_of_ratings
from book_info import get_regular_price
from book_info import get_book_length
from book_info import get_language


def audible_scraper(URL):
    # returns df with scaped books
    webpage = requests.get(URL)
    soup = BeautifulSoup(webpage.text, "html.parser")

    # part that scrapes book info
    books_data = soup.find_all('li', class_='bc-list-item productListItem')
    df = pd.DataFrame(
        {
            "Book_Name": get_book_names(books_data),
            "Description": get_description(books_data),
            "Author": get_author(books_data),
            "Rating": get_rating(books_data),
            "Num_of_Ratings": get_nunber_of_ratings(books_data),
            "Regular_Price": get_regular_price(books_data),
            "Audio_Length": get_book_length(books_data),
            "Language": get_language(books_data)
        })

    # part that scrapes category or your search results for author or book
    # maybe append 'Category' col ot df
    pass

    return df
