package com.inditex.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inditex.application.service.PricesService;
import com.inditex.domain.model.Prices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/prices")
@Tag(name = "Inditex API", description = "API de Precios para obtener el precio final (pvp) de un producto en una cadena específica y fecha determinada.")
public class PricesController {

	private final PricesService pricesService;

	@Autowired
	public PricesController(PricesService pricesService) {
		this.pricesService = pricesService;
	}

	@GetMapping("/")
	@Operation(summary = "Obtener todos los precios", description = "Obtiene una lista de todos los precios disponibles en el sistema.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista de precios obtenida correctamente", 
			content = @Content(array = @ArraySchema(schema = @Schema(implementation = Prices.class)))),
		@ApiResponse(responseCode = "204", description = "No hay precios disponibles"),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public ResponseEntity<List<Prices>> getPricesAll() {
		try {
			List<Prices> prices = pricesService.getPricesAll();
			if (prices.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(prices, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{apply}/{productId}/{brandId}")
	@Operation(summary = "Obtener un precio por condiciones", 
		description = "Obtiene el precio aplicable según la fecha de aplicación, el ID del producto y el ID de la marca.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Precio encontrado", 
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Prices.class))),
		@ApiResponse(responseCode = "204", description = "No se encontró un precio para los criterios dados"),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public ResponseEntity<Prices> getPricesConditional(
			@Parameter(description = "Fecha de aplicación del precio", example = "2024-02-04T00:00:00") @PathVariable String apply,
			@Parameter(description = "ID del producto") @PathVariable Long productId,
			@Parameter(description = "ID de la cadena o marca") @PathVariable Long brandId) {
		try {
			Optional<Prices> prices = pricesService.getPricesConditional(apply, productId, brandId);
			return prices.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/")
	@Operation(summary = "Crear un precio", description = "Crea un nuevo registro de precio en el sistema.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Precio creado correctamente", 
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = Prices.class))),
		@ApiResponse(responseCode = "304", description = "Solicitud incorrecta"),
		@ApiResponse(responseCode = "500", description = "Error interno del servidor")
	})
	public ResponseEntity<Prices> savePrices(
			@Parameter(description = "Datos del nuevo precio a registrar") @RequestBody Prices prices) {
		try {
			Prices pricesResult = pricesService.savePrices(prices);
			if (pricesResult != null) {
				return new ResponseEntity<>(pricesResult, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
