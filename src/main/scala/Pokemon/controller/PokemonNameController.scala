package Pokemon.controller

import Pokemon.model.{Bulbasaur, Charmander, Pokemon, Squirtle, PokemonBoard}
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, TextField, ButtonType}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml
import scalafx.scene.image.{Image, ImageView}
import javafx.scene.layout.StackPane

@sfxml
class PokemonNameDialogController (

      private val pokemonNameField : TextField,

      private var pokemonStackPane : StackPane,

      private var pokemonImage : ImageView

) {

  var dialogStage : Stage  = null
  var pokemon : Pokemon = PokemonBoard.pokemon
  var addClicked : Boolean = false

  val pokemonImg = new Image("images/" + pokemon.getClass.getSimpleName + "Front.png")
  pokemonImage = new ImageView(pokemonImg)
  pokemonStackPane.getChildren().add(pokemonImage)
  

  def handleCreatePokemon(action: ActionEvent) : Unit = {
    
    var errorMessage = ""

    if (pokemonNameField.text.value == null || pokemonNameField.text.value.length == 0) {

        val alert = new Alert(Alert.AlertType.Error) {
        
            initOwner(dialogStage)
            title = "Invalid Fields"
            headerText = "Please correct invalid fields"
            contentText = "Invalid pokemon name!\n"
        }.showAndWait()

    } else {

        pokemon.givenName <== pokemonNameField.text

        // if Pokemon exists, confirm replace
        if (PokemonBoard.isExist(pokemon)) {

            val alert = new Alert(Alert.AlertType.Confirmation) {

                initOwner(dialogStage)
                title = "Pokemon Exists"
                headerText = "Create this Pokemon will replace your previous Pokemon.\n Do you want to do this?"
                contentText = "Pokemon Exists"
            }

            val confirmSave = alert.showAndWait()

            confirmSave match {

                case Some(ButtonType.OK) => addClicked = true
                    
                case _ => addClicked = false    
            }
        } else {

            addClicked = true
        }
        dialogStage.close()
        }
    }
}
