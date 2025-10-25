function formatTime(iso) {
  if (!iso) return 'never';
  try {
    const d = new Date(iso);
    if (isNaN(d)) return iso;
    const locale = navigator.language || 'en-US';
    const abs = d.toLocaleString(locale);
    // relative time
    const diff = Date.now() - d.getTime();
    const seconds = Math.floor(diff / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    let rel = '';
    if (seconds < 10) rel = 'just now';
    else if (seconds < 60) rel = `${seconds}s ago`;
    else if (minutes < 60) rel = `${minutes}m ago`;
    else if (hours < 24) rel = `${hours}h ago`;
    else rel = `${days}d ago`;
    return `${abs} (${rel})`;
  } catch (e) {
    return iso;
  }
}

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
      <div>Last watered: ${formatTime(p.lastWateredAt)}</div>
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
