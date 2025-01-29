
package com.crio.warmup.stock.quotes;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.SECONDS;
import com.crio.warmup.stock.dto.AlphavantageCandle;
import com.crio.warmup.stock.dto.AlphavantageDailyResponse;
import com.crio.warmup.stock.dto.Candle;
import com.crio.warmup.stock.util.AlphavantageCandleComparator;
import com.crio.warmup.stock.exception.StockQuoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cglib.core.Local;
import org.springframework.web.client.RestTemplate;

public class AlphavantageService implements StockQuotesService {

  static String token="4V7SCD5CWMSVIF5X";

  public static String getToken(){
    return token;
  }

  private RestTemplate restTemplate;

  public AlphavantageService(RestTemplate restTemplate){
    this.restTemplate=restTemplate;
  }

  @Override
  public List<Candle> getStockQuote(String symbol, LocalDate from, LocalDate to)
      throws JsonProcessingException, StockQuoteServiceException {
        String url=buildUri(symbol);
        String responseString;
        try{
          responseString= restTemplate.getForObject(url, String.class);
          ObjectMapper objectMapper=getObjectMapper();
          AlphavantageDailyResponse response=objectMapper.readValue(responseString, AlphavantageDailyResponse.class);
          // Case 1: Response contains no data
          if (response == null || response.getCandles() == null || response.getCandles().isEmpty()) {
            throw new StockQuoteServiceException("Response contains no data for symbol: " + symbol);
          }
          List<Candle> candles=new ArrayList<>();
          for(LocalDate date : response.getCandles().keySet()){
            if(date.isAfter(from) && date.isBefore(to) || date.equals(from) || date.equals(to)){
              AlphavantageCandle alcandle = response.getCandles().get(date);
              alcandle.setDate(date);
              candles.add(alcandle);
            } 
          }
        // Case 2: Response contains an error or invalid data
        if (candles.isEmpty()) {
          throw new StockQuoteServiceException("No valid data found for symbol: " + symbol);
        }
        Collections.sort(candles, new AlphavantageCandleComparator());
        return candles;
      }catch(JsonMappingException e){
        throw new StockQuoteServiceException("ex");
      }catch(RuntimeException e){
        throw new StockQuoteServiceException("Unexpected error occurred while fetching stock data for symbol: " + symbol, e);
      }
        
  }

  protected String buildUri(String symbol) {
    String url="https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+symbol+"&apikey="+token;
    return url;
}

private static ObjectMapper getObjectMapper() {
  ObjectMapper objectMapper = new ObjectMapper();
  objectMapper.registerModule(new JavaTimeModule());
  return objectMapper;
}

  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  Implement the StockQuoteService interface as per the contracts. Call Alphavantage service
  //  to fetch daily adjusted data for last 20 years.
  //  Refer to documentation here: https://www.alphavantage.co/documentation/
  //  --
  //  The implementation of this functions will be doing following tasks:
  //    1. Build the appropriate url to communicate with third-party.
  //       The url should consider startDate and endDate if it is supported by the provider.
  //    2. Perform third-party communication with the url prepared in step#1
  //    3. Map the response and convert the same to List<Candle>
  //    4. If the provider does not support startDate and endDate, then the implementation
  //       should also filter the dates based on startDate and endDate. Make sure that
  //       result contains the records for for startDate and endDate after filtering.
  //    5. Return a sorted List<Candle> sorted ascending based on Candle#getDate
  //  IMP: Do remember to write readable and maintainable code, There will be few functions like
  //    Checking if given date falls within provided date range, etc.
  //    Make sure that you write Unit tests for all such functions.
  //  Note:
  //  1. Make sure you use {RestTemplate#getForObject(URI, String)} else the test will fail.
  //  2. Run the tests using command below and make sure it passes:
  //    ./gradlew test --tests AlphavantageServiceTest
  //CHECKSTYLE:OFF
    //CHECKSTYLE:ON
  // TODO: CRIO_TASK_MODULE_ADDITIONAL_REFACTOR
  //  1. Write a method to create appropriate url to call Alphavantage service. The method should
  //     be using configurations provided in the {@link @application.properties}.
  //  2. Use this method in #getStockQuote.
  // TODO: CRIO_TASK_MODULE_EXCEPTIONS
  //   1. Update the method signature to match the signature change in the interface.
  //   2. Start throwing new StockQuoteServiceException when you get some invalid response from
  //      Alphavantage, or you encounter a runtime exception during Json parsing.
  //   3. Make sure that the exception propagates all the way from PortfolioManager, so that the
  //      external user's of our API are able to explicitly handle this exception upfront.
  //CHECKSTYLE:OFF

}

