
package com.crio.warmup.stock.quotes;

import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.dto.TiingoCandle;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class TiingoService implements StockQuotesService {

  private RestTemplate restTemplate;

  
  static String token="29b2c1a67c469b10c66a09b1344fb74fd42178ea";

  public static String getToken(){
    return token;
  }
  
  protected TiingoService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException, StockQuoteServiceException {
    String url = buildUri(symbol,from,to);
    try{
    String response = restTemplate.getForObject(url, String.class);
    if (response == null || response.isEmpty()) {
      throw new StockQuoteServiceException("Response contains no data for symbol: " + symbol);
    }
    ObjectMapper objectMapper=getObjectMapper();
      TiingoCandle[] candles =   objectMapper.readValue(response, TiingoCandle[].class);
    if (candles.length<=0) {
      throw new StockQuoteServiceException("No valid data found for symbol: " + symbol);
    }
    return List.of(candles);
    }catch(JsonProcessingException e){
      throw new StockQuoteServiceException("Error processing JSON response for symbol: " + symbol, e);
    }catch(RuntimeException e){
      throw new StockQuoteServiceException("Unexpected error occurred while fetching stock data for symbol: " + symbol, e);
    }
  }

  protected String buildUri(String symbol, LocalDate startDate, LocalDate endDate) {
    String url="https://api.tiingo.com/tiingo/daily/"+symbol+"/prices?startDate="+startDate+"&endDate="+endDate+"&token="+token;
    return url;
}

private static ObjectMapper getObjectMapper() {
  ObjectMapper objectMapper = new ObjectMapper();
  objectMapper.registerModule(new JavaTimeModule());
  return objectMapper;
}


  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement getStockQuote method below that was also declared in the interface.

  // Note:
  // 1. You can move the code from PortfolioManagerImpl#getStockQuote inside newly created method.
  // 2. Run the tests using command below and make sure it passes.
  //    ./gradlew test --tests TiingoServiceTest


  //CHECKSTYLE:OFF

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Write a method to create appropriate url to call the Tiingo API.





  // TODO: CRIO_TASK_MODULE_EXCEPTIONS
  //  1. Update the method signature to match the signature change in the interface.
  //     Start throwing new StockQuoteServiceException when you get some invalid response from
  //     Tiingo, or if Tiingo returns empty results for whatever reason, or you encounter
  //     a runtime exception during Json parsing.
  //  2. Make sure that the exception propagates all the way from
  //     PortfolioManager#calculateAnnualisedReturns so that the external user's of our API
  //     are able to explicitly handle this exception upfront.

  //CHECKSTYLE:OFF


}
