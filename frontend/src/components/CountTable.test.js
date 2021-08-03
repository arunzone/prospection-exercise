import React from 'react';
import {mount} from "enzyme";
import CountTable from "./CountTable";

describe('Count table', () => {
    describe('Header', () => {
        const appComponent = mount(<CountTable data={[]}/>);
        const titles = appComponent.find('th')
        test('first header should have type title', () => {
            const header = titles.at(0).text()
            expect(header).toEqual('Types');
        });
        test('second header should have type title', () => {
            const header = titles.at(1).text()
            expect(header).toEqual('Count');
        });
    });
    describe('Rows', () => {
        const data = [
            {
                name: 'Verbs', count: 5
            }
        ]
        const appComponent = mount(<CountTable data={data}/>);
        const contents = appComponent.find('td')
        test('first column should have type title', () => {
            const content = contents.at(0).text()
            expect(content).toEqual('Verbs');
        });
        test('second column should have type title', () => {
            const content = contents.at(1).text()
            expect(content).toEqual('5');
        });
    });
    describe('No Rows', () => {
        test('first column should have type title', () => {
            const appComponent = mount(<CountTable />);
            const content = appComponent.find('td')
            expect(content.exists()).toBe(false);
        });
    });
});
