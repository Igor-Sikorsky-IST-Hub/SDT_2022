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
from book_info import get_page_URL
from book_info import get_cover_image_URL


class AudibleScraper:
    def __init__(self, URL):
        self.URL = URL

    def perform_scraping(self):
        # returns df with scaped books
        webpage = requests.get(self.URL)
        soup = BeautifulSoup(webpage.text, "html.parser")

        # part that scrapes book info
        books_data = soup.find_all('li', class_='bc-list-item productListItem')
        return pd.DataFrame(
            {
                "title": get_book_names(books_data),
                "description": get_description(books_data),
                "author": get_author(books_data),
                "rating": get_rating(books_data),
                "rating_count": get_nunber_of_ratings(books_data),
                "reg_price": get_regular_price(books_data),
                "audio_len": get_book_length(books_data),
                "language": get_language(books_data),
                "page_URL": get_page_URL(books_data),
                "cover_image_URL": get_cover_image_URL(books_data)
            })

    def change_URL(self, URL):
        self.URL = URL
