package org.shoppingcart.component;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.shoppingcart.dto.client.ClientDTO;
import org.shoppingcart.dto.client.CreditCardDetailsDTO;
import org.shoppingcart.dto.login.Role;
import org.shoppingcart.dto.order.OrderDTO;
import org.shoppingcart.dto.order.OrderDetailDTO;
import org.shoppingcart.dto.order.OrderProductDTO;
import org.shoppingcart.dto.payment.PayPalPaymentRequestDTO;
import org.shoppingcart.dto.payment.PaymentRequestDTO;
import org.shoppingcart.dto.payment.PaymentResponseDTO;
import org.shoppingcart.dto.product.ProductDTO;
import org.shoppingcart.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Component
@RequiredArgsConstructor
public class MemoryDB {

    private int orderIdSequence = 1;
    private int clientIdSequence = 1;
    private int productIdSequence = 1;
    private int paymentIdSequence = 1;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final Map<Integer, OrderDTO> orders = new HashMap<>();
    private final Map<Integer, PaymentRequestDTO> payments = new HashMap<>();
    public static final Map<Integer, ClientDTO> clients = new HashMap<>();
    private final Map<Integer, ProductDTO> products = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(MemoryDB.class);

    @Value("${external.api.fakestore.url}")
    private String urlAPI;


    /**
     * ************************************************ *
     * * * Start methods and functions for clients/users * *
     * * *************************************************
     */
    public void addAllClients() {
        ResponseEntity<ClientDTO[]> response = restTemplate.getForEntity(urlAPI + "/users", ClientDTO[].class);
        ClientDTO[] Listclients = response.getBody();

        if (Listclients == null || Listclients.length == 0) {
            logger.info("There are no clients available in API");
        } else {
            clientIdSequence = Listclients.length;
            clients.clear();
            Arrays.stream(Listclients).forEach(client -> {
                client.setPassword(passwordEncoder.encode(client.getPassword()));
                client.setRole(client.getUsername().equals("kevinryan") ? Role.ADMIN : Role.USER);
                clients.put(client.getId(), client);
            });
        }
    }

    public Optional<ClientDTO> getClientById(Integer id) {
        return Optional.ofNullable(clients.get(id));
    }

    public ClientDTO getClientByName(String username) {
        return clients.values().stream()
                .filter(client -> client.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Collection<ClientDTO> getAllClients() {
        if (!clients.isEmpty()) {
            return clients.values();
        } else {
            throw new NotFoundException("There are no registered customers/users in the database.");
        }
    }

    /** ************************************************ *
     ** * Ends methods and functions for clients/users * *
     ** ************************************************ */

    //////////////////////////////////////////////////////////////////////////

    /**
     * ******************************************* *
     * * * Start methods and functions for products * *
     * * ********************************************
     */

    public void addAllProducts() {
        ResponseEntity<ProductDTO[]> response = restTemplate.getForEntity(urlAPI + "/products", ProductDTO[].class);
        ProductDTO[] ListProducts = response.getBody();

        if (ListProducts == null || ListProducts.length == 0) {
            logger.info("There are no products available in API");
        } else {
            productIdSequence = ListProducts.length;
            products.clear(); // Clear the map before adding new data (optional)
            Arrays.stream(ListProducts).forEach(product -> products.put(product.getId(), product));
        }
    }

    public ProductDTO addProduct(ProductDTO product) {
        product.setId(productIdSequence++);
        products.put(product.getId(), product);
        return product;
    }


    public ProductDTO updateProduct(ProductDTO product) {
        products.put(product.getId(), product);
        return product;
    }

    public boolean deleteProduct(Integer id) {
        ProductDTO product = products.get(id);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        products.remove(id);
        return true;
    }

    public Optional<ProductDTO> getProductById(Integer id) {
        return Optional.ofNullable(products.get(id));
    }

    public Collection<ProductDTO> getAllProducts() {
        if (!products.isEmpty()) {
            return products.values();
        } else {
            throw new NotFoundException("There are no registered products in the database.");
        }
    }

    /** ******************************************* *
     ** * Ends methods and functions for products * *
     ** ******************************************* */

    //////////////////////////////////////////////////////////////////////////

    /**
     * ***************************************** *
     * * * Start methods and functions for orders * *
     * * ******************************************
     */

    public OrderDTO addOrder(OrderDetailDTO order) {
        Optional<ClientDTO> client = getClientById(order.getUserId());
        if (client.isPresent()) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(orderIdSequence++);
            orderDTO.setUserId(order.getUserId());
            orderDTO.setDate(order.getDate());
            orderDTO.setOrderTotal(calculateOrderTotal(order));
            orderDTO.setProducts(order.getProducts());

            orders.put(orderDTO.getId(), orderDTO);
            return orderDTO;
        } else {
            throw new NotFoundException("The customer indicated in the order does not exist.");
        }
    }

    public Optional<OrderDTO> getOrderById(Integer id) {
        return Optional.ofNullable(orders.get(id));
    }


    public Collection<OrderDTO> getAllOrders() {
        if (!orders.isEmpty()) {
            return orders.values();
        } else {
            throw new NotFoundException("There are no registered orders in the database.");
        }
    }


    public BigDecimal calculateOrderTotal(OrderDetailDTO order) {
        List<OrderProductDTO> productDTOList = order.getProducts();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderProductDTO product : productDTOList) {
            ProductDTO foundProduct = getProductById(product.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found: " + product.getProductId()));

            if (product.getQuantity() > 0) {
                BigDecimal productPrice = BigDecimal.valueOf(foundProduct.getPrice());
                BigDecimal quantity = BigDecimal.valueOf(product.getQuantity());
                BigDecimal subtotal = productPrice.multiply(quantity);

                total = total.add(subtotal);
            } else {
                throw new NotFoundException("La cantidad no debe ser negativa");
            }
        }

        return total;
    }

/** ***************************************** *
 ** * Ends methods and functions for orders * *
 ** ***************************************** */

//////////////////////////////////////////////////////////////////////////

    /**
     * ******************************************* *
     * * * Start methods and functions for payments * *
     * * ********************************************
     */
    public PaymentResponseDTO addPayment(PaymentRequestDTO payment) {
        Optional<OrderDTO> order = getOrderById(payment.getOrderId());
        if (order.isPresent()) {
            if (Boolean.FALSE.equals(order.get().getPaymentStatus())) {
                return switch (payment.getPaymentMethod()) {
                    case "CREDIT_CARD" -> processCreditCardPayment(payment, order.get());
                    case "PAYPAL" -> processPayPalPayment(payment, order.get());
                    default -> throw new NotFoundException(payment.getPaymentMethod());
                };
            } else {
                throw new NotFoundException("The order is already cancelled.");
            }
        } else {
            throw new NotFoundException("The order indicated in the payment does not exist.");
        }
    }

    public void addPaymentMemory(PaymentRequestDTO payment) {
        payment.setId(paymentIdSequence++);
        payments.put(payment.getId(), payment);
    }

    private PaymentResponseDTO processCreditCardPayment(PaymentRequestDTO request, OrderDTO order) {
        // Validate card
        if (request.getCardDetails() == null) {
            throw new NotFoundException("Card details required");
        }

        // Simulate authorization
        boolean isApproved = authorize(
                request.getCardDetails()
        );

        if (isApproved) {
            order.setPaymentStatus(true);
            orders.put(order.getId(), order);
            addPaymentMemory(request);
            return PaymentResponseDTO.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .status("SUCCESS")
                    .message("Payment approved")
                    .timestamp(LocalDateTime.now())
                    .build();
        } else {
            throw new NotFoundException("Card declined");
        }
    }

    private PaymentResponseDTO processPayPalPayment(PaymentRequestDTO request, OrderDTO order) {
        //Validate PayPal-specific data
        if (request.getPaypalEmail() == null || request.getPaypalEmail().isBlank()) {
            throw new NotFoundException("Email is required");
        }else {
            if (!request.getPaypalEmail().matches("^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|outlook\\.com|hotmail\\.com)$")) {
                throw new NotFoundException("Email format is invalid");
            }
        }

        //Create a request to the PayPal API
        PayPalPaymentRequestDTO paypalRequest = PayPalPaymentRequestDTO.builder()
                .email(request.getPaypalEmail())
                .amount(order.getOrderTotal())
                .currency(request.getCurrency())
                .description("Order #" + order.getId())
                .build();

        try {
            //At this point in the logic to execute payment in Paypal
            //PayPalPaymentResponse paypalResponse = paypalClient.createPayment(paypalRequest);
            String paypalResponse = "APPROVED";
            String confirmedPayment = "111";
            //
            if ("APPROVED".equals(paypalResponse)) {
                order.setPaymentStatus(true);
                orders.put(order.getId(), order);
                addPaymentMemory(request);
                return PaymentResponseDTO.builder()
                        .transactionId("PP-" + confirmedPayment)
                        .status("SUCCESS")
                        .message("Payment captured successfully")
                        .timestamp(LocalDateTime.now())
                        .build();
            } else {
                throw new NotFoundException("PayPal payment not approved.");
            }

        } catch (Exception e) {
            logger.error("Failed to process PayPal payment: {}", ExceptionUtils.getStackTrace(e));
            throw new NotFoundException("Failed to process PayPal payment: " + e.getMessage());
        }
    }

    public boolean authorize(CreditCardDetailsDTO card) {
        try {
            //Validate basic data format
            Objects.requireNonNull(card, "Card details cannot be null");
            validateCardNumber(card.getCardNumber());
            validateCVV(card.getCvv());

            //Mock authorization logic
            boolean isCardNumberValid = Integer.parseInt(card.getCardNumber().substring(card.getCardNumber().length() - 1)) % 2 == 0;
            boolean isCVVValid = Integer.parseInt(card.getCvv()) % 2 == 0;
            boolean isDateValid = !isCardExpired(card.getExpiryDate());

            //Only approve if ALL validations are successful
            return isCardNumberValid && isCVVValid && isDateValid;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isCardExpired(String expiryDate) {
        //Expected format: MM/yy
        String[] parts = expiryDate.split("/");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid expiry date format. Use MM/yy");
        }

        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]) + 2000; // Asume años 2000+

        //Validate month (1-12)
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month");
        }

        //Get current date and
        YearMonth current = YearMonth.now();
        YearMonth expiry = YearMonth.of(year, month);

        //Check if the card has already expired
        return expiry.isBefore(current);
    }

    private void validateCVV(String cvv) {
        //CVV must be 3 or 4 numeric digits
        if (!cvv.matches("^\\d{3,4}$")) {
            throw new IllegalArgumentException("CVV must be 3 or 4 digits");
        }
    }

    private void validateCardNumber(String cardNumber) {
        //Remove spaces and hyphens
        String cleaned = cardNumber.replaceAll("[\\s-]+", "");

        // Validate length (13 to 19 digits according to ISO/IEC 7812 standard)
        if (!cleaned.matches("^\\d{13,19}$")) {
            throw new IllegalArgumentException("Invalid card number length");
        }

        // Validate with Luhn algorithm (checksum)
        if (!luhnCheck(cleaned)) {
            throw new IllegalArgumentException("Invalid card number (Luhn check failed)");
        }
    }

    // Luhn algorithm for validating card numbers
    private boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) digit = (digit % 10) + 1;
            }
            sum += digit;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

/** ******************************************* *
 ** * Ends methods and functions for payments * *
 ** ******************************************* */
}
