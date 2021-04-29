const {app, BrowserWindow, Menu} = require('electron');
const path = require('path');
Menu.setApplicationMenu(false);

app.on('window-all-closed', function() {
    if (process.platform != 'darwin') {app.quit();}
});

app.on('ready', function() {
    app.allowRendererProcessReuse = false;
    let mainWin = new BrowserWindow({width: 800, height: 600, webPreferences: {nodeIntegration: true, contextIsolation: false}});
    mainWin.loadFile('app/index.html');
    mainWin.on('closed', function() {mainWin = null;})
})