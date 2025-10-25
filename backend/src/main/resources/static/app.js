async function listPlants(){
  const res = await fetch('/api/plants');
  const plants = await res.json();
  const container = document.getElementById('plants');
  container.innerHTML = '';
  plants.forEach(p => {
    const div = document.createElement('div');
    div.className = 'plant';
    div.innerHTML = `<strong>${p.name}</strong> <small>${p.species || ''}</small>
      <div>Pot: ${p.potVolumeLiters || ''} L</div>
      <div>Soil: ${p.soilType || ''}</div>
      <div>Last watered: ${p.lastWateredAt || 'never'}</div>
      <div>Total water: ${p.totalWateredMl || 0} ml</div>
      <div><input type="number" placeholder="ml" class="water-input" /> <button class="water-btn">Water</button></div>
    `;
    const btn = div.querySelector('.water-btn');
    const input = div.querySelector('.water-input');
    btn.addEventListener('click', async () => {
      const ml = parseInt(input.value || '0', 10);
      await fetch('/api/plants/' + p.id + '/water', { method:'POST', headers:{'content-type':'application/json'}, body: JSON.stringify({ volumeMl: ml, source: 'manual' }) });
      await listPlants();
    });
    container.appendChild(div);
  });
}

document.getElementById('add').addEventListener('click', async () => {
  const dto = {
    name: document.getElementById('name').value,
    species: document.getElementById('species').value,
    potVolumeLiters: parseFloat(document.getElementById('pot').value || '0'),
    soilType: document.getElementById('soil').value
  };
  await fetch('/api/plants', { method: 'POST', headers: {'content-type':'application/json'}, body: JSON.stringify(dto) });
  document.getElementById('name').value='';
  document.getElementById('species').value='';
  document.getElementById('pot').value='';
  document.getElementById('soil').value='';
  await listPlants();
});

window.addEventListener('load', listPlants);
