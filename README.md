# Example application how to set MDC for Webflux

Inspired by [hint from Spring One](https://github.com/OlegDokuka/reactor-by-examples/blob/master/src/main/java/com/example/demo/ExampleMDC.java).

Using the same code is not working with Spring Webflux- MDC context is not passed into Reactor part:

```
2021-11-11 10:04:35.099  INFO 49909 --- [           main] c.matejuh.webfluxmdc.WeatherController   : request_id=fd8f7621-0a5c-44a3-882e-8b8e1185d3d2 action=getWeather
2021-11-11 10:04:35.856  INFO 49909 --- [ctor-http-nio-2] webClient                                : request_id= method=GET uri=https://wttr.in?AT status=200 OK
2021-11-11 10:04:35.881  INFO 49909 --- [           main] com.matejuh.webfluxmdc.LoggingFilter     : request_id=fd8f7621-0a5c-44a3-882e-8b8e1185d3d2 method=GET uri=/weather status=200
```
