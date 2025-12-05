async function getStreetInfo(address) {
  let response = await fetch(`/street-info/${address}`);
  if (!response.ok) throw new Error('street information unavailable');
  return await response.json();
}

async function getStreetBlocks(address) {
  let response = await fetch(`/street-blocks/${address}`);
  if (!response.ok) throw new Error('block information unavailable');
  return await response.json();
}

async function DisplaySideStreet(street, street_children_element) {
  if (street['children']) {
    for (let i = 0; i < street['children'].length; i++) {
      let child = await getStreetInfo(street['children'][i]['address']);
      let address = child['self']['address'];
      let sub_street = await getStreetInfo(address);
      
      street_children_element.insertAdjacentHTML(
        'beforeend', `<div class="inset-element ${i == street['children'].length - 1 ? 'final' : 'full'}">
                        <div id="street-${address}"></div>
                      </div>`
      );

      DisplayStreet(sub_street, street_children_element.querySelector(`#street-${address}`));
    }
  }
}

async function DisplayStreet(street, attatch_element) {
  let street_template = document.getElementById('street-info-template');
  let block_template  = document.getElementById('block-info-template');

  let street_element = street_template.content.cloneNode(true);

  // fill basic data
  street_element.getElementById('street-name').textContent = street['self']['name'];
  street_element.getElementById('street-address').textContent = street['self']['address'];
  street_element.getElementById('street-description').textContent = street['self']['description'];
  street_element.getElementById('street-child-count').textContent = street['children'].length;

  // fill block data
  let blocks = await getStreetBlocks(street['self']['address']);
  for (let i = 0; i < blocks.length; i++) {
    let block_element = block_template.content.cloneNode(true);

    if (i == blocks.length - 1)
      block_element.querySelector('.inset-element').classList.add('final');
    else 
      block_element.querySelector('.inset-element').classList.add('full');

    block_element.getElementById('block-name').textContent = blocks[i]['name'];
    block_element.getElementById('block-address').textContent = blocks[i]['address'];

    street_element.getElementById('street-children').appendChild(block_element);
  };

  DisplaySideStreet(street, street_element.getElementById('street-children'));
  attatch_element.appendChild(street_element);
}

window.addEventListener('DOMContentLoaded', async () => {
  // start with root and enter recursion for display
  streets = await getStreetInfo('root');
  DisplayStreet(streets, document.getElementById('street-layout-box'));
});