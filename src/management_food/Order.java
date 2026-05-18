package management_food;

public class Order {
    private final int id;
    private final String dishName;
    private final String category;
    private final int quantity;
    private final String deliveryMethod;
    private final String address;

    public Order(int id, String dishName, String category, int quantity, String deliveryMethod, String address) {
        this.id = id;
        this.dishName = dishName;
        this.category = category;
        this.quantity = quantity;
        this.deliveryMethod = deliveryMethod;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getDishName() {
        return dishName;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getAddress() {
        return address;
    }
}
