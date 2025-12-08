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

function formatAddress(address) {
  if (address == 'root')
    return address;

  let result = '';
  for (let i = 0; i < address.length; i += 2) {
    result += address.slice(i, i + 2) + (i == address.length - 2 ? '' : '-');
  }
  return result;
}

function findNextChildElement(currentAddress, street_element) {
    let currentNum = parseInt(currentAddress.slice(-2)); 
    let streetElements = Array.from(street_element.querySelectorAll('#street-children .inset-element, #street-children .inset-street'));

    streetElements = streetElements
        .map(el => ({
            element: el,
            value: parseInt(el.querySelector('#block-address,#street-address').textContent.slice(-2))
        }))
        .filter(item => item.value > currentNum) 
        .sort((a, b) => a.value - b.value); 

    return streetElements[0] ? streetElements[0].element : null;
}

async function DisplaySideStreet(street, street_children_element) {
  if (street['children']) {
    for (let i = 0; i < street['children'].length; i++) {
      let child = await getStreetInfo(street['children'][i]['address']);
      let address = child['self']['address'];
      let sub_street = await getStreetInfo(address);

      let add_element = findNextChildElement(address, street_children_element);

      let html = `<div class="inset-street ${add_element == null ? 'final final-street' : 'full'}" draggable="true">
              <div id="street-${address}"></div>
            </div>`;

      if (add_element != null)
        add_element.insertAdjacentHTML('beforebegin', html);
      else
        street_children_element.insertAdjacentHTML('beforeend', html);

      DisplayStreet(sub_street, street_children_element.querySelector(`#street-${address}`));
    }
  }
}

async function DisplayStreet(street, attatch_element) {
  let street_template = document.getElementById('street-info-template');
  let block_template  = document.getElementById('block-info-template');

  let street_element = street_template.content.cloneNode(true);

  // fill basic data
  street_element.id = 'street-'+street['self']['address'];
  street_element.getElementById('street-name').textContent = street['self']['name'];
  street_element.getElementById('street-address').textContent = formatAddress(street['self']['address']);

  // fill block data
  let blocks = await getStreetBlocks(street['self']['address']);
  for (let i = 0; i < blocks.length; i++) {
    let block_element = block_template.content.cloneNode(true);

    if (i == blocks.length - 1)
      block_element.querySelector('.inset-element').classList.add('final');
    else 
      block_element.querySelector('.inset-element').classList.add('full');

    block_element.getElementById('block-name').textContent = blocks[i]['name'];
    block_element.getElementById('block-address').textContent = formatAddress(blocks[i]['address']);

    street_element.getElementById('street-children').appendChild(block_element);
  };

  // set button events
  street_element.getElementById('street-toggle-expand').addEventListener('click', () => {
    el = document.getElementById(`street-${street['self']['address']}`);
    el.querySelector('#street-children').classList.toggle('hidden');
    el.querySelector('#street-toggle-expand').classList.toggle('active');
  });

  street_element.getElementById('street-delete').addEventListener('click', () => {
    el = document.getElementById(`street-${street['self']['address']}`);

    alert('This  will remove ...');
  });

  DisplaySideStreet(street, street_element.getElementById('street-children'));
  attatch_element.appendChild(street_element);
}

function AddNewStreet() {
  console.log('test');
  document.getElementById('add-street-section').classList.remove('hidden');
}

window.addEventListener('DOMContentLoaded', async () => {
  // start with root and enter recursion for display
  streets = await getStreetInfo('root');
  DisplayStreet(streets, document.getElementById('street-root'));
});