docker run -e POSTGRES_USER=root -e POSTGRES_PASSWORD=root -e POSTGRES_DB=khmyl_app -p 5432:5432 library/postgres

TODO: 
weekly/monthly/quarter(last 7/14 days, last month, previous month... add callbacks support?) reports in pdf (last 7/14 days, last month, previous month (in diff formats?(PDF/csv email?  //add some interface for diff types???)
add error command
ALL NBRB currencies button
add more bank rates(with exposed APIs)
user settings to chose currencies and time of notifications, banks
upd timezone dependent msg ???
more text info in commands/responses+(info command?)
currency converter?
buttons response ??
refactor text provider (add placeholders for msgs and params) and resolver (localization?) +-
extract currency service to separate service?(micronaut if possible feign?)

~~delete chat -> send msg by job+~~
~~cache~~
