function() {

    var League =
      'fragment League on League {\
        id\
        name\
        continent\
     }';
  
  
    return {
      League: function () { return [League]}
  }
}