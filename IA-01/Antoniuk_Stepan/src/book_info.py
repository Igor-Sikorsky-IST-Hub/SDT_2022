# many thanks to https://jovian.ai/pratulofficialthings/audible-business-and-careers-books-2022
def get_book_names(data):
    book_names = []
    for tag in data:
        a_tag_name = tag.h3.find_all('a', recursive=False)
        book_name = a_tag_name[0].text.strip()
        book_names.append(book_name)
    return book_names


def get_book_length(data):
    book_length = []
    for tag in data:
        try:
            len_tag = tag.find('li', class_='bc-list-item runtimeLabel')
            length_tag = len_tag.find('span')
            length = length_tag.text.strip()
            book_length.append(length)
        except AttributeError:
            book_length.append(None)
    # Remove "Length: " from every elem of output list if it's not NoneType
    book_length = [i[8:] if i is not None else i for i in book_length]
    return book_length


def get_author(book_contents):
    written_by = []
    for tag in book_contents:
        author_tag = tag.find('li', class_='bc-list-item authorLabel')
        try:
            auth_tag = author_tag.find('a')
            author = auth_tag.text.strip()
            written_by.append(author)
        except AttributeError:
            written_by.append(None)
    return written_by


def get_description(data):
    description = []
    for tag in data:
        about_tag = tag.find('li', class_='bc-list-item subtitle')
        try:
            description_tag = about_tag.find('span').text.strip()
            description.append(description_tag)
        except AttributeError:
            description.append(None)
    return description


def get_language(data):
    language_list = []
    for tag in data:
        lang_tag = tag.find('li', class_='bc-list-item languageLabel')
        try:
            language_tag = lang_tag.find('span').text.split()
            # Access 'English' in ['Language:', 'English'] of language_tag
            language_itself = language_tag[1]
            language_list.append(language_itself)
        except AttributeError:
            language_list.append(None)
    return language_list


def get_rating(data):
    rating = []
    for tag in data:
        star_tag = tag.find('li', class_='bc-list-item ratingsLabel')
        try:
            rating_tag = star_tag.find(
                'span', class_='bc-text bc-pub-offscreen').text.strip()
            rating.append(rating_tag)
        except AttributeError:
            rating.append(None)
    return rating


def get_nunber_of_ratings(data):
    nunber_of_ratings = []
    for tag in data:
        star_tag = tag.find('li', class_='bc-list-item ratingsLabel')
        try:
            rating_tag = star_tag.find(
                'span',
                class_='bc-text bc-size-small bc-color-secondary').text.strip()
            nunber_of_ratings.append(rating_tag)
        except AttributeError:
            nunber_of_ratings.append(None)
    return nunber_of_ratings


def get_regular_price(data):
    regular_price = []
    for tag in data:
        buy_tag = tag.find(
            'p',
            class_='bc-text buybox-regular-price bc-spacing-none bc-spacing-top-none')
        try:
            price_tag = buy_tag.find_all(
                'span', class_='bc-text bc-size-base bc-color-base')
            price = price_tag[1].text.strip()
            regular_price.append(price)
        except IndexError:
            # Handle book on Daily Deal price
            price_tag = buy_tag.find_all(
                'span',
                class_='bc-text bc-size-small bc-color-secondary bc-text-strike')
            price = price_tag[0].text.strip()
            regular_price.append(price)
        except AttributeError:
            regular_price.append(None)
    return regular_price


# probably requires login, so put off for now
def get_member_price(data):
    pass


def get_narrator(data):
    pass


def get_cover_image_URL(data):
    cover_img = []
    for tag in data:
        img_tag = tag.find_all(
            'img', class_='bc-pub-block bc-image-inset-border js-only-element')
        try:
            book_image_url = img_tag[0]['src'].strip()
            cover_img.append(book_image_url)
        except AttributeError:
            cover_img.append(None)
    return cover_img


def get_page_URL(data):
    base_url = 'https://www.audible.com'
    book_links = []
    for tag in data:
        a_tag_name = tag.h3.find_all('a', recursive=False)
        url = a_tag_name[0]['href'].strip()
        book_link = base_url+url
        book_links.append(book_link)
    return book_links
