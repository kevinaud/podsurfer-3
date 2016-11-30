FB.getLoginStatus(function(response){
  if(response.status === 'connected'){
    console.log('Logged in.');
  }
  else{
    console.log("not logged in");
    FB.login();
  }
});