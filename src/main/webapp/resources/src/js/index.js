import {drawCanvasGraph} from "./graph";
import "../css/main.css"


document.addEventListener('DOMContentLoaded', function () {
    drawCanvasGraph([], 1);
})

window.drawDots = drawCanvasGraph
