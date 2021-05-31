import React from 'react';
import { Switch, Route } from 'react-router-dom';

import UserTable from './Components/UserTable';
import MapComponent from './Components/MapComponent';
import AmiTable from './Components/AmisTable';

const Main = () => {
  return (
    <Switch> {/* The Switch decides which component to show based on the current URL.*/}
      <Route exact path='/' component={UserTable}></Route>
      <Route exact path='/map' component={MapComponent}></Route>
      <Route exact path='/amis' render={(props) => <AmiTable {...props} />}></Route>
    </Switch>
  );
}

export default Main;