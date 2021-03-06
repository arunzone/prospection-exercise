import React, {useState} from 'react';
import axios from 'axios';
import {Container, Grid, Header} from 'semantic-ui-react';
import CountTable from './components/CountTable';
import CountChart from './components/CountChart';
import TextInput from './components/TextInput';

import './App.css';
import {transformResult} from "./helpers/GrammarResultTransformer";

const _mockedResults = {
  status: 'VALID',
  types: [
    {
      name: 'Verbs', count: 4
    },
    {
      name: 'Nouns', count: 8
    },
    {
      name: 'Prepositions', count: 4
    }
  ],
  violations: [
    {
      name: 'Rule 1', count: 4
    },
    {
      name: 'Rule 2', count: 8
    },
    {
      name: 'Rule 3', count: 4
    },
    {
      name: 'Rule 4', count: 9
    }
  ]
}


const App = () => {
  const [ results, setResults ] = useState(_mockedResults)

  const onApply = (text) => {
    axios.post('/api/analysis', text, { headers: { 'Content-Type': 'text/plain' } })
      .then(({ data }) => {
        // setResults(_mockedResults)
        setResults(transformResult(data))
      })
  }

  return (
    <div className='App'>
      <Container>
        <Grid columns={2} padded>
          <Grid.Row>
            <Grid.Column width={16}>
              <Header as='h2'>Paragraph</Header>
              <TextInput onApply={onApply} />
            </Grid.Column>
          </Grid.Row>
          {results && <React.Fragment>
              <Grid.Row>
              <Grid.Column width={16}>
                <Header as='h2' data-test-id='status-title'>Status: {results.status}</Header>
              </Grid.Column>
            </Grid.Row>
            <Grid.Row>
              <Grid.Column width={16}>
                <Header as='h2'>Type vs Count</Header>
              </Grid.Column>
            </Grid.Row>
            <Grid.Row>
              <Grid.Column>
                <CountTable data={results.types} label='Types' />
              </Grid.Column>
              <Grid.Column>
                <CountChart data={results.types} label='Types' />
              </Grid.Column>
            </Grid.Row>
            <Grid.Row>
              <Grid.Column width={16}>
                <Header as='h2'>Violation vs Count</Header>
              </Grid.Column>
            </Grid.Row>
            <Grid.Row>
              <Grid.Column>
                <CountTable data={results.violations} label='Violations' />
              </Grid.Column>
              <Grid.Column>
                <CountChart data={results.violations} label='Violations' />
              </Grid.Column>
            </Grid.Row>
          </React.Fragment>}
        </Grid>
      </Container>
    </div>
  );
}

export default App;
