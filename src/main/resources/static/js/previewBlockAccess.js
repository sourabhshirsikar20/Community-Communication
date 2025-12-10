let editSvgs = {
    base: [null, null],
    roof: [null, null],
    foreground: [null, null]
};

const EDIT_SVG_PATHS = {
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

function loadEditSVG(type, number) {
    const path = EDIT_SVG_PATHS[type][number];

    fetch(path)
        .then(response => response.text())
        .then(svgText => {
            editSvgs[type][number] = svgText;
            updatePreview();
        })
        .catch(error => console.error('Error loading', path, error));
}

function updatePreview() {
    const blockName =
        (document.getElementById('blockName')?.value) ||
        (document.getElementById('blockNameHidden')?.value) ||
        'Your Block';

    const roofOption = parseInt(document.getElementById('roofOption').value || '1');
    const baseOption = parseInt(document.getElementById('baseOption').value || '1');
    const foregroundOption = parseInt(document.getElementById('foregroundOption').value || '1');

    const roofColour = document.getElementById('roofColour').value;
    const baseColour = document.getElementById('baseColour').value;
    const foregroundColour = document.getElementById('foregroundColour').value;

    // --- BASE ---
    if (editSvgs.base[baseOption]) {
        const container = document.getElementById('preview-base');
        container.innerHTML = editSvgs.base[baseOption];

        const svg = container.querySelector('svg');
        if (svg) {
            const fillElement = svg.querySelector('#fill_col');
            if (fillElement) {
                fillElement.setAttribute('fill', baseColour);
            }
            const textElement = svg.querySelector('text');
            if (textElement) {
                textElement.textContent = blockName;
            }
        }
    }

    // --- ROOF ---
    if (editSvgs.roof[roofOption]) {
        const container = document.getElementById('preview-roof');
        container.innerHTML = editSvgs.roof[roofOption];

        const svg = container.querySelector('svg');
        if (svg) {
            const groups = svg.querySelectorAll('#fill_col');
            groups.forEach(g => g.setAttribute('fill', roofColour));
        }
    }

    // --- FOREGROUND ---
    if (editSvgs.foreground[foregroundOption]) {
        const container = document.getElementById('preview-foreground');
        container.innerHTML = editSvgs.foreground[foregroundOption];

        const svg = container.querySelector('svg');
        if (svg) {
            const fillElements = svg.querySelectorAll('#fill_col');
            fillElements.forEach(elem => elem.setAttribute('fill', foregroundColour));
        }
    }

    const roofLabel = document.getElementById('roofColourValue');
    if (roofLabel) roofLabel.textContent = roofColour.toUpperCase();

    const baseLabel = document.getElementById('baseColourValue');
    if (baseLabel) baseLabel.textContent = baseColour.toUpperCase();

    const fgLabel = document.getElementById('foregroundColourValue');
    if (fgLabel) fgLabel.textContent = foregroundColour.toUpperCase();
}

window.addEventListener('DOMContentLoaded', () => {
    loadEditSVG('base', 1);
    loadEditSVG('base', 2);
    loadEditSVG('roof', 1);
    loadEditSVG('roof', 2);
    loadEditSVG('foreground', 1);
    loadEditSVG('foreground', 2);
});
