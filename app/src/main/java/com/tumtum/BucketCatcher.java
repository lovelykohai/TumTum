package com.tumtum;

import java.util.Random;

public class BucketCatcher {
    //To translate any possible food requests into something the database can connect
    public static String CreateRealDesire(){
        //Check the want and shift to real want
        //For example, the database only recognises "coffee_shop" but users typically
        //Type "coffee", "tea" or "cafe" the BucketCatcher should catch the text into
        //Something the database understands
        String Want = SomethingToEat.ReturnWant();
        Want = Want.toUpperCase();
        switch(Want){
            case "SOMETHING":
            case "I DON'T KNOW":
                Random random = new Random();
                int i = random.nextInt(5 - 1 + 1) + 1;
                switch(i){
                    case 1:
                        Want = "pizza";
                        break;
                    case 2:
                        Want = "burger";
                        break;
                    case 3:
                        Want = "italian";
                        break;
                    case 4:
                        Want = "indian";
                        break;
                    case 5:
                        Want = "greek";
                        break;
                }
            case "COFFEE":
            case "TEA":
            case "CAFE":
            case "CAFÉ":
            case "TEA ROOM":
            case "COFFEE_SHOP":
            case "COFFEE SHOP":
                Want = "coffee_shop";
                break;
            case "PIZZA":
                Want = "pizza";
                break;
            case "AMERICAN":
            case "HOT DOG":

                Want = "american";
                break;
            case "BARBECUE":
            case "BBQ":
                Want = "barbecue";
                break;
            case "BRITISH":
                Want = "british";
                break;
            case "BANGLADESHI":
                Want = "bangladeshi";
                break;
            case "CARIBBEAN":
                Want = "caribbean";
                break;
            case "CHICKEN":
                Want = "chicken";
                break;
            case "DESSERT":
            case "ICE CREAM":
            case "CAKE":
            case "MILKSHAKE":
                Want = "dessert";
                break;
            case "GREEK":
                Want = "greek";
                break;
            case "FRENCH":
                Want = "french";
                break;
            case "BEER":
            case "PUB":
            case "BOOZE":
            case "DRINK":
            case "ALCOHOL":
                Want = "alcohol";
                break;
            case "CHINESE":
            case "JAPANESE":
            case "CHINESE FOOD":
            case "ASIAN":
            case "ASIAN FOOD":
            case "SUSHI":
                Want = "asian";
                break;
            case "HAMBURGER":
            case "HAM BURGER":
            case "CHEESE BURGER":
            case "BURGER":
            case "CHEESEBURGER":
            case "CHICKEN BURGER":
                Want = "burger";
                break;
            case "BUBBLE TEA":
            case "BUBBLETEA":
            case "BUBBLE CHA":
            case "BUBBLE_TEA":
            case "BOBA":
            case "BOBA CHA":
                Want = "bubble_tea";
                break;
            case "FISH":
            case "FISH AND CHIPS":
            case "CHIPS":
            case "CHIPPY":
                Want = "fish_and_chips";
                break;
            case "CURRY":
            case "INDIAN":
                Want = "indian";
                break;
            case "ITALIAN":
            case "PASTA":
            case "SPAGHETTI":
            case "CARBONARA":
                Want = "italian";
                break;
            case "KEBAB":
                Want = "kebab";
                break;
            case "LEBANESE":
            case "SCHWARMA":
            case "SHAWARMA":
                Want = "lebanese";
                break;
            case "MEXICAN":
                Want = "mexican";
                break;
            case "SANDWICH":
                Want = "sandwich";
                break;
            case "THAI":
                Want = "thai";
                break;
            case "STEAK":
            case "STEAK HOUSE":
                Want= "steak";
                break;
            default:
                Want = "";
        }
        return Want;
    }
}
