def check_book_availability(book_title_to_check, df):
    # check if the book is present in df
    # if not, finish the program straightaway
    # otherwise, check whether it was ranked before
    # Availability of rating is sufficient to check whether you can buy it or not
    if book_title_to_check in df["title"].values:
        print(f"\nThe book with the title '{book_title_to_check}' is present in the catalogue")
        book_characteristics = df.loc[df['title'] == book_title_to_check]
        if book_characteristics["rating_count"].values.all() == "Not rated yet":
            print("but unfortunately isn't available for buying yet")
        elif book_characteristics["rating"].values.all() is None:
            print("but unfortunately isn't available for buying yet")
        else:
            print("and is available for buying")
    else:
        print(f"\nThe book with the title '{book_title_to_check}' isn't present in the catalogue")
