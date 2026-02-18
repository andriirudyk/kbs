package com.rudyk.kitchenbar.api.controllers;

import com.rudyk.kitchenbar.api.dto.CreateProduct;
import com.rudyk.kitchenbar.api.dto.CreateTicketRequest;
import com.rudyk.kitchenbar.api.dto.ProductDTO;
import com.rudyk.kitchenbar.api.dto.ProductResponse;
import com.rudyk.kitchenbar.domain.model.Product;
import com.rudyk.kitchenbar.services.KitchenBarService;
import com.rudyk.kitchenbar.services.ProductService;
import com.rudyk.kitchenbar.services.TicketManagementService;
import com.rudyk.kitchenbar.domain.model.Ticket;
import com.rudyk.kitchenbar.domain.vo.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kitchenbar/tickets")
@Validated
public class KitchenBarController {


    private final KitchenBarService kitchenBarService;
    private final TicketManagementService ticketManagementService;
    private ProductService productService;

    public KitchenBarController(KitchenBarService kitchenBarService,
                                TicketManagementService ticketManagementService,
                                ProductService productService) {
        this.kitchenBarService = kitchenBarService;
        this.ticketManagementService = ticketManagementService;
        this.productService = productService;
    }

    // --- Create ticket ---
    @PostMapping
    public TicketResponse create(@RequestBody @Valid CreateTicketRequest req) {
        List<MenuItem> items = (req.getItems() == null)
                ? new ArrayList<>()
                : req.getItems().stream()
                .map(MenuItem::new)   // MenuItem(ItemRequest)
                .collect(Collectors.toList());

        Ticket ticket = kitchenBarService.createTicket(items);
        return TicketResponse.from(ticket);
    }

    // --- Get by ID ---
    @GetMapping("/{ticketId}")
    public TicketResponse getById(@PathVariable Long ticketId) {
        Ticket t = kitchenBarService.getTicket(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Ticket not found: " + ticketId));
        return TicketResponse.from(t);
    }

    // --- Start ticket (move to IN_PROGRESS) ---
    @PostMapping("/{ticketId}/start")
    public TicketResponse start(@PathVariable Long ticketId,
                                @RequestBody @Valid StartTicketRequest req) {
        Ticket t = ticketManagementService.startTicket(ticketId, req.stationOrAssignee);
        return TicketResponse.from(t);
    }

    // --- Add preparation delay ---
    @PostMapping("/{ticketId}/delay")
    public TicketResponse delay(@PathVariable Long ticketId,
                                @RequestBody @Valid DelayRequest req) {
        Ticket t = ticketManagementService.addPreparationDelay(
                ticketId,
                req.extraMinutes,
                req.reason
        );
        return TicketResponse.from(t);
    }

    // --- Mark ready ---
    @PostMapping("/{ticketId}/ready")
    public TicketResponse markReady(@PathVariable Long ticketId) {
        Ticket t = ticketManagementService.completeTicket(ticketId);
        return TicketResponse.from(t);
    }

    // --- Reassign ticket ---
    @PostMapping("/{ticketId}/reassign")
    public TicketResponse reassign(@PathVariable Long ticketId,
                                   @RequestBody @Valid ReassignRequest req) {
        Ticket t = ticketManagementService.reassign(ticketId, req.newStationOrAssignee);
        return TicketResponse.from(t);
    }

    // add product
    @PostMapping("/product")
    public ProductResponse addProduct(@RequestBody @Valid CreateProduct createProduct) {
        return productService.createProduct(createProduct);
    }

    @GetMapping("/product/get")
    public List<ProductResponse> getProduct() {
        System.out.println("get product endpoint");
        return productService.getProducts();
    }
    @PutMapping("/product")
    public ProductResponse updateProduct(@RequestBody @Valid ProductDTO product) {
        return productService.updateProduct(product);
    }
    @DeleteMapping("/product/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        System.out.println("delete product endpoint");
        productService.deleteProduct(productId);
    }
    // --- List active (not READY) ---
    @GetMapping("/active")
    public List<TicketResponse> listActive() {
        return ticketManagementService.getActiveTickets().stream()
                .map(TicketResponse::from)
                .collect(Collectors.toList());
    }

    // --- Recently completed since timestamp ---
    @GetMapping("/completed-since")
    public List<TicketResponse> completedSince(@RequestParam("since") String sinceIso) {
        LocalDateTime since = LocalDateTime.parse(sinceIso);
        return ticketManagementService.getRecentlyCompleted(since).stream()
                .map(TicketResponse::from)
                .collect(Collectors.toList());
    }

    // --- Exception handling ---

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> notFound(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("BAD_REQUEST", ex.getMessage()));
    }

    // ===== Request DTOs =====

    public static class StartTicketRequest {
        public String stationOrAssignee; // Optional — who/where it's being prepared
    }

    public static class DelayRequest {
        @Min(1)
        public int extraMinutes;

        @NotBlank
        public String reason;
    }

    public static class ReassignRequest {
        @NotBlank
        public String newStationOrAssignee;
    }

    // ===== Response DTOs =====

    public static class TicketResponse {
        public Long id;
        public List<TicketItemResponse> items;
        public String status;
        public String stationOrAssignee;
        public String createdAt;
        public String startedAt;
        public String completedAt;
        public Integer lastDelayMinutes;
        public String lastDelayReason;

        public static TicketResponse from(Ticket t) {
            TicketResponse dto = new TicketResponse();
            dto.id = t.getId();
            dto.items = t.getItems().stream()
                    .map(TicketItemResponse::from)
                    .collect(Collectors.toList());
            dto.status = t.getStatus().name();
            dto.stationOrAssignee = t.getStationOrAssignee();
            dto.createdAt = t.getCreatedAt() != null ? t.getCreatedAt().toString() : null;
            dto.startedAt = t.getStartedAt() != null ? t.getStartedAt().toString() : null;
            dto.completedAt = t.getCompletedAt() != null ? t.getCompletedAt().toString() : null;
            dto.lastDelayMinutes = t.getLastDelayMinutes();
            dto.lastDelayReason = t.getLastDelayReason();
            return dto;
        }
    }

    public static class TicketItemResponse {
        public String itemCode;
        public String displayName;
        public int quantity;

        public static TicketItemResponse from(MenuItem item) {
            TicketItemResponse dto = new TicketItemResponse();
            dto.itemCode = item.getCode();
            dto.displayName = item.getDisplayName();
            dto.quantity = item.getQuantity();
            return dto;
        }
    }

    public static class ApiError {
        public String code;
        public String message;

        public ApiError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}