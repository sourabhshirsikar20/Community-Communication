let svgs = {
    base: [null, null],
    roof: [null, null],
    foreground: [null, null]
};

const SVG_PATHS = {
    base: {
        1: '/svg/base/base-1.svg',
        2: '/svg/base/base-2.svg'
    },
    roof: {
        1: '/svg/roof/roof-1.svg',
        2: '/svg/roof/roof-2.svg'
    },
    foreground: {
        1: '/svg/foreground/foreground-1.svg',
        2: '/svg/foreground/foreground-2.svg'
    }
};

function loadSVG(type, number) {
    const path = SVG_PATHS[type][number];

    fetch(path)
        .then(response => response.text())
        .then(svgText => {
            svgs[type][number] = svgText;
            updatePreview();
        })
        .catch(error => console.error('Error loading', path, error));
}

function updatePreview() {
    let blockName = (document.getElementById('blockName')?.value) ||
        (document.getElementById('blockNameHidden')?.value) || 'Your Block';
    
    let roofOption = parseInt(document.getElementById('roofOption').value);
    let baseOption = parseInt(document.getElementById('baseOption').value);
    let foregroundOption = parseInt(document.getElementById('foregroundOption').value);
    
    let roofColour = document.getElementById('roofColour').value;
    let baseColour = document.getElementById('baseColour').value;
    let foregroundColour = document.getElementById('foregroundColour').value;
    let textColour = document.getElementById('textColour').value;

    if (svgs.base[baseOption]) {  
        let container = document.getElementById('preview-base');
        
        container.innerHTML = svgs.base[baseOption];
        
        let svg = container.querySelector('svg');
        
        if (svg) {
            let fillElement = svg.querySelector('#fill_col');
            if (fillElement) {
                fillElement.setAttribute('fill', baseColour);
            }
            
            let textElement = svg.querySelector('text');
            if (textElement) {
                textElement.textContent = blockName;
                textElement.setAttribute('fill', textColour);
            }
        }
    }

    if (svgs.roof[roofOption]) {  
        let container = document.getElementById('preview-roof');
        container.innerHTML = svgs.roof[roofOption];
        let svg = container.querySelector('svg');
        
        if (svg) {
            let groups = svg.querySelectorAll('#fill_col');
            
            groups.forEach(function(g) {
                g.setAttribute('fill', roofColour);
            });
        }
    }

    if (svgs.foreground[foregroundOption]) {  
        let container = document.getElementById('preview-foreground');
        container.innerHTML = svgs.foreground[foregroundOption];
        let svg = container.querySelector('svg');
        
        if (svg) {
            let fillElements = svg.querySelectorAll('#fill_col');
            fillElements.forEach(function(elem) {
                elem.setAttribute('fill', foregroundColour);
            });
        }
    }

    document.getElementById('roofColourValue').textContent = roofColour.toUpperCase();
    document.getElementById('baseColourValue').textContent = baseColour.toUpperCase();
    document.getElementById('foregroundColourValue').textContent = foregroundColour.toUpperCase();
    document.getElementById('textColourValue').textContent = textColour.toUpperCase();
    UpdateText();
}

function UpdateText() {
    let text_element = document.getElementById('preview-base').querySelector('text');
    let grad_slider  = document.getElementById('fontGrade');

    let is_serif  = document.getElementById('fontSerif').checked;
    let is_italic = document.getElementById('fontItalic').checked;
    let weight    = document.getElementById('fontWeight').value;
    let width     = document.getElementById('fontWidth').value;
    let grade     = grad_slider.value;

    if (is_serif) {
        grad_slider.removeAttribute('disabled');
        if (text_element) {
            text_element.style.fontFamily = '"Roboto serif", serif';
            text_element.style.fontVariationSettings = `'wght' ${weight}, 'wdth' ${width}, 'GRAD' ${grade}`;
        }
    }
    else {
        grad_slider.setAttribute('disabled', '');
        if (text_element) {
            text_element.style.fontFamily = '"Roboto", sans-serif';
            text_element.style.fontVariationSettings = `'wght' ${weight}, 'wdth' ${width}`;
        }
    }

    if (text_element) {
        text_element.style.fontStyle = is_italic ? 'italic' : 'normal';
        text_element.style.letterSpacing = `${(width-75)/(25)*(0.15)+0.05}em`; // scales width to a range 0.05-0.2 em
    }
}

window.addEventListener('DOMContentLoaded', () => {
    loadSVG('base', 1);
    loadSVG('base', 2);
    loadSVG('roof', 1);
    loadSVG('roof', 2);
    loadSVG('foreground', 1);
    loadSVG('foreground', 2);
    UpdateText();
});