package HW.a1;

//represents a real estate listing
class RealEstateListing {
  boolean singleFamily;
  int year;
  int squareFootage;
  int price;
  String city;

  RealEstateListing(boolean singleFamily, int year, int squareFootage, int price, String city) {
    this.singleFamily = singleFamily;
    this.year = year;
    this.squareFootage = squareFootage;
    this.price = price;
    this.city = city;
  }
  /* TEMPLATE:
     Fields:
     ... this.singleFamily ...         -- boolean
     ... this.year ...                 -- int
     ... this.squareFootage ...        -- int
     ... this.price ...                -- int
     ... this.city ...                 -- String
  */
}

class ExamplesListings {

  ExamplesListings() {}

  RealEstateListing bostonCondo = new RealEstateListing(false, 2010, 700, 350000, "Boston");
  RealEstateListing beachHouse = new RealEstateListing(true, 1995, 2000, 699999, "Yarmouth");
  RealEstateListing countryFarm = new RealEstateListing(true, 1856, 5000, 275000, "Kansas");
}