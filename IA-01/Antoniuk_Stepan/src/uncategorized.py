def check_book_availability(book_name_to_check, df):
    # check if the book is present in df
    # if not, finish the program straightaway
    # otherwise, check whether it was ranked before
    # Availability of rating is sufficient to check whether you can buy it or not
    if book_name_to_check in df["Book_Name"].values:
        print(f"\nThe book with the title '{book_name_to_check}' is present in the catalogue")
        book_characteristics = df.loc[df['Book_Name'] == book_name_to_check]
        if book_characteristics["Num_of_Ratings"].values.all() == "Not rated yet":
            print("but unfortunately isn't available for buying yet")
        elif book_characteristics["Rating"].values.all() is None:
            print("but unfortunately isn't available for buying yet")
        else:
            print("and is available for buying")
    else:
        print(f"\nThe book with the title '{book_name_to_check}' isn't present in the catalogue")
