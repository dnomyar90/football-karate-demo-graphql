function() {

    var Player =
      'fragment Player on Player {\
        id\
        first_name\
        last_name\
        team{\
          ...Team\
        }\
     }';
  
  
    return {
      Player: function () { return [Player,
        karate.call('classpath:fragments/FragmentTeam.js').Team()
      ]
    }
  }
}