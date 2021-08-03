import React from 'react';
import {Table} from 'semantic-ui-react';

const CountTable = ({ data, label }) => (
    <Table celled fixed singleLine>
        <Table.Header>
            <Table.Row>
                <Table.HeaderCell>Types</Table.HeaderCell>
                <Table.HeaderCell>Count</Table.HeaderCell>
            </Table.Row>
        </Table.Header>

        <Table.Body>
            { data && data.map((entry, index) =>
                <Table.Row key={index}>
                    <Table.Cell>{entry.name}</Table.Cell>
                    <Table.Cell>{entry.count}</Table.Cell>
                </Table.Row>
            )}
        </Table.Body>
    </Table>
)

export default CountTable