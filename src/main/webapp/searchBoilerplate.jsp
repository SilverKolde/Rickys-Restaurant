<h2>Find your favourite dish</h2>
<div class="preSelected">
    <a href='searchResults.html?searchTerm=chicken' class="m-3 btn btn-info" type="button">Chicken dishes</a>
    <a href='searchResults.html?searchTerm=lamb' class="m-3 btn btn-info" type="button">Lamb dishes</a>
    <a href='searchResults.html?searchTerm=rice' class="m-3 btn btn-info" type="button">Rice dishes</a>
</div>
<form action="${pageContext.request.contextPath}/searchResults.html" method="GET">
    Find all dishes containing : <input type="text" name="searchTerm" id="searchTerm" /> <input type="submit" value="search" />
</form>