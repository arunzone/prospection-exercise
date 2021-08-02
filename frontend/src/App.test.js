import React from 'react';
import {render} from '@testing-library/react';
import App from './App';
import {shallow} from "enzyme";

describe('Grammar analysis page', () => {
  test('renders paragraph label', () => {
    const {getByText} = render(<App/>);
    const linkElement = getByText(/paragraph/i);
    expect(linkElement).toBeInTheDocument();
  });

  test('renders valid status title', () => {
    const appComponent = shallow(<App />);
    const title = appComponent.find({ 'data-test-id': 'status-title' })
    expect(title.dive().text()).toBe('Status: VALID');
  });
});
