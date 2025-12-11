// renderHeader();
// renderFooter();

let svgs = {};

function escapeRegExp(string) {
    return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
}

async function injectSvgs() {
    const containers = document.querySelectorAll('.js-inject-svg');
    const searchInput = document.getElementById('search_value');
    const searchQuery = searchInput ? searchInput.value.trim() : '';

    for (const container of containers) {
        if (container.querySelector('svg')) continue;
        const url = container.getAttribute('data-url');
        const color = container.getAttribute('data-color');
        const textToInject = container.getAttribute('data-text');

        try {
            const response = await fetch(url);
            if (!response.ok) throw new Error(`Failed to load ${url}`);
            
            const svgText = await response.text();
            const parser = new DOMParser();
            const doc = parser.parseFromString(svgText, 'image/svg+xml');
            const svgElement = doc.querySelector('svg');

            let groups = svgElement.querySelectorAll('#fill_col');
            groups.forEach(function(g) {
                g.setAttribute('fill', color);
            });

            if (textToInject) {

                const existingText = svgElement.querySelector('text');
                if (existingText) {
                    if (searchQuery && textToInject.toLowerCase().includes(searchQuery.toLowerCase())) {
                        
                        existingText.textContent = ''; 

                        const regex = new RegExp(`(${escapeRegExp(searchQuery)})`, 'gi');
                        const parts = textToInject.split(regex);

                        parts.forEach(part => {
                            if (part.toLowerCase() === searchQuery.toLowerCase()) {
                                const tspan = document.createElementNS("http://www.w3.org/2000/svg", "tspan");
                                tspan.textContent = part;
                                tspan.classList.add('search-highlight'); // Apply CSS class
                                existingText.appendChild(tspan);
                            } else {
                                existingText.appendChild(document.createTextNode(part));
                            }
                        });
                    } else {
                        existingText.textContent = textToInject;
                    }
                    existingText.setAttribute('fill', container.getAttribute('data-fontColour'));

                    // set font options
                    let fontOptions = container.getAttribute('data-fontOptions');
                    if (fontOptions) {
                        let style = JSON.parse(fontOptions);
                        if (style['serif']) {
                            existingText.style.fontFamily = '"Roboto serif", serif';
                            existingText.style.fontVariationSettings = `'wght' ${style['weight']}, 'wdth' ${style['width']}, 'GRAD' ${style['grade']}`;
                        }
                        else {
                            existingText.style.fontFamily = '"Roboto", sans-serif';
                            existingText.style.fontVariationSettings = `'wght' ${style['weight']}, 'wdth' ${style['width']}`;
                        }

                        existingText.style.fontStyle = style['italic'] ? 'italic' : 'normal';
                        existingText.style.letterSpacing = `${(style['width']-75)/(25)*(0.15)+0.05}em`;
                    }
                } 
            }

            container.appendChild(svgElement);

        } catch (error) {
            console.error('Error injecting SVG:', error);
        }
    }
}

function findNextStreetElement(currentAddress) {
    let currentNum = parseInt(currentAddress.slice(-2)); 
    
    let streetElements = Array.from(document.querySelectorAll('a[data-address]'))
        .map(el => ({
            element: el,
            address: el.dataset.address,
            value: parseInt(el.dataset.address.slice(-2))
        }))
        .filter(item => item.value > currentNum) 
        .sort((a, b) => a.value - b.value); 
    
    return streetElements[0] ? streetElements[0].element : null;
}

async function injectStreets() {
    let street_addresses = document.getElementById("address-range");
    let addresses = JSON.parse(street_addresses.dataset.paginationlist);

    // load svgs
    let response = await fetch(`/svg/street.svg`);
    if (!response.ok) throw new Error('svgs unavailable');
    svgs['street'] = await response.text();

    response = await fetch(`/svg/street-return.svg`);
    if (!response.ok) throw new Error('svgs unavailable');
    svgs['street-return'] = await response.text();

    // get street information
    let current_street = window.location.pathname;
    let current_address = null;

    if (current_street == "/Index")
        current_address = "root";
    else
        current_address = current_street.split("/").pop();

    // get street information
    response = await fetch(`/street-info/${current_address}`);
    if (!response.ok) throw new Error('street information unavailable');
    let street_info = await response.json();
    
    // add node to return to previous street
    if (street_info['parent'] != null) {
        document.querySelector('.street-container').insertAdjacentHTML(
            'afterbegin',
            `
            <a id="street-return" class="house-block" href="/street/${street_info['parent']['address']}">
                ${svgs['street-return']}
            </a>
            `
        );

        document.querySelector(`#street-return text`).textContent = street_info['parent']['name'];
    }

    // add nodes making sub streets
    for (let i = 0; i < street_info['children'].length; i++) {
        let addr_int = parseInt(street_info['children'][i].address.slice(-2));
        if (addr_int >= addresses[0] && addr_int <= addresses[1]) {
            let next_element = findNextStreetElement(street_info['children'][i].address);
            let mode = 'beforebegin';

            if (next_element == null) {
                next_element = document.querySelector('.street-container');
                mode = 'beforeend';
            }
            
            next_element.insertAdjacentHTML(
                mode,
                `
                <a id="street-${street_info['children'][i]['address']}" data-address="${street_info['children'][i]['address']}" class="house-block" href="/street/${street_info['children'][i]['address']}">
                    ${svgs['street']}
                </a>
                `
            );

            document.querySelector(`#street-${street_info['children'][i]['address']} text`).textContent = street_info['children'][i]['name'];
        }
    }
    //Puts Ghost blocks by their address number
    const streetContainer = document.querySelector('.street-container');
    if (streetContainer) {
        const ghosts = Array.from(document.querySelectorAll('.house-block.moved-block'));
        ghosts.forEach(ghost => {
            const addr = ghost.dataset.address;
            if (!addr) return;

            const next = findNextStreetElement(addr);
            if (next && next !== ghost) {
                //Ghost block have to come after before the first block with greater adress number
                streetContainer.insertBefore(ghost, next);
            } else {
                streetContainer.appendChild(ghost);
            }
        });
    }
}

injectSvgs();
setTimeout(() => {
    const msg = document.querySelector('.success-message');
    if (msg) {
        msg.style.transition = "opacity 1s ease";
        msg.style.opacity = 0;
        setTimeout(() => msg.remove(), 1000);
    }
}, 5000);

document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('search_value');
    const streetContainer = document.querySelector('.street-container');

    if (searchInput && streetContainer) {
        searchInput.addEventListener('input', async (e) => {
            const query = e.target.value;
            
            try {
                const response = await fetch(`/live-search?value=${encodeURIComponent(query)}`);
                if (!response.ok) throw new Error('Search failed');
                
                const blocks = await response.json();
                
                streetContainer.innerHTML = '';

                blocks.forEach(block => {
                    const link = document.createElement('a');
                    link.className = 'house-block';
                    link.href = `/OrganisationInfo/${block.address}`;
                    link.dataset.id = block.id;


                    const createLayer = (type, option, color, text = null) => {
                        const div = document.createElement('div');
                        div.className = 'house-layer js-inject-svg';
                        div.dataset.url = `/svg/${type}/${type}-${option}.svg`;
                        div.dataset.color = color;
                        if (text) div.dataset.text = text;
                        return div;
                    };

                    link.appendChild(createLayer('base', block.baseOption, block.baseColour, block.name));
                    link.appendChild(createLayer('roof', block.roofOption, block.roofColour));
                    link.appendChild(createLayer('foreground', block.foregroundOption, block.foregroundColour));

                    streetContainer.appendChild(link);
                });

                injectSvgs();

            } catch (error) {
                console.error('Live search error:', error);
            }
        });
    }
    
    injectStreets();
});
