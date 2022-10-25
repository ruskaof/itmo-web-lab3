import {initializeCanvasGraph} from "./graph";
import "../css/main.css"
import {includeValidation} from "./validation";


document.addEventListener('DOMContentLoaded', function() {
    initializeCanvasGraph()
    includeValidation()
})