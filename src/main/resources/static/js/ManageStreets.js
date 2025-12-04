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

async function getStreetChildren(street) {
  let children = [];

  for (let i = 0; i < street['children'].length; i++) {
    let child = await getStreetInfo(street['children'][i]['address']);
    child['children'] = await getStreetChildren(child);
    children.push(child);
  }

  return children.length == 0 ? null : children;
}

async function displayStreet(street) {
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
  console.log(blocks);
  for (let i = 0; i < blocks.length; i++) {
    let block_element = block_template.content.cloneNode(true);

    if (i == blocks.length - 1)
      block_element.querySelector('.inset-element').classList.add('final');
    else 
      block_element.querySelector('.inset-element').classList.add('full');

    block_element.getElementById('block-name').textContent = blocks[i]['name'];

    street_element.getElementById('street-children').appendChild(block_element);
  };

  document.getElementById('street-layout-box').appendChild(street_element);
}

window.addEventListener('DOMContentLoaded', async () => {
  // start with root and enter recursion
  streets = await getStreetInfo('root');
  streets['children'] = await getStreetChildren(streets);

  console.log(streets);

  displayStreet(streets);
});